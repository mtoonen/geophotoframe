package nl.meine.gpf.stripes;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import nl.meine.gpf.entities.Gebruiker;
import nl.meine.gpf.entities.Geoservice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geotools.data.FeatureSource;
import org.geotools.data.ows.Layer;
import org.geotools.data.ows.WMSCapabilities;
import org.geotools.data.wfs.v1_1_0.WFSFeatureSource;
import org.geotools.data.wms.WebMapServer;
import org.geotools.jdbc.JDBCFeatureSource;
import org.geotools.ows.ServiceException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.stripesstuff.stripersist.EntityTypeConverter;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Meine Toonen
 */
public class ServiceActionBean implements ActionBean {

    private static final Log log = LogFactory.getLog(ServiceActionBean.class);
    private ActionBeanContext context;
    private static final String OVERVIEW = "/WEB-INF/jsp/admin/services/servicesoverview.jsp";
    private static final String EDIT = "/WEB-INF/jsp/admin/services/edit.jsp";
    @Validate(converter = EntityTypeConverter.class)
    @ValidateNestedProperties({
        @Validate(field = "name", required = true, on = "save"),
        @Validate(field = "description", required = true, on = "save"),
        @Validate(field = "url", required = true, on = "save"),
        @Validate(field = "type", required = true, on = "save")
    })
    private Geoservice service;
    // Used for filtering
    @Validate
    private JSONArray filter;
    @Validate
    private String sort;
    @Validate
    private String dir;
    @Validate
    private int limit;
    @Validate
    private int start;
    @Validate
    private int page;

    @DefaultHandler
    public Resolution view() {

        return new ForwardResolution(OVERVIEW);
    }

    public Resolution add() {
        return new ForwardResolution(EDIT);

    }

    public Resolution save() {
        try {
            WebMapServer wms = new WebMapServer(new URL(service.getUrl()));
            WMSCapabilities capabilities = wms.getCapabilities();
            Layer l = capabilities.getLayer();
            service.setLayers(l.getName());
            service.setDescription(service.getDescription() + "; " + l.get_abstract());

            EntityManager em = Stripersist.getEntityManager();
            Gebruiker user = (Gebruiker) context.getRequest().getUserPrincipal();
            user = em.find(Gebruiker.class, user.getId());
            service.setGebruiker(user);
            em.persist(service);

            em.getTransaction().commit();
        } catch (IOException ex) {
            log.error("Could not make URL of " + service.getUrl(), ex);
            context.getValidationErrors().add("service.url", new SimpleError("Service URL not valid"));
        } catch (ServiceException ex) {
            log.error("Problem doing getcapabilities", ex);
            context.getValidationErrors().add("Service", new SimpleError("Can not retrieve getcapabilities"));
        } catch (Exception e) {

            log.error("Problem doing getcapabilities", e);
            context.getValidationErrors().add("Service", new SimpleError("Can not retrieve getcapabilities"));
        }
        return new ForwardResolution(OVERVIEW);

    }

    public Resolution getGridData() throws JSONException {
        JSONArray jsonData = new JSONArray();

        String filterName = "";
        String filterUrl = "";
        String filterType = "";
        String filterDescription = "";
        String filterUser = "";
        /*
         * FILTERING: filter is delivered by frontend as JSON array [{property,
         * value}] for demo purposes the value is now returned, ofcourse here
         * should the DB query be built to filter the right records
         */
        if (this.getFilter() != null) {
            for (int k = 0; k < this.getFilter().length(); k++) {
                JSONObject j = this.getFilter().getJSONObject(k);
                String property = j.getString("property");
                String value = j.getString("value");
                if (property.equals("name")) {
                    filterName = value;
                }
                if (property.equals("url")) {
                    filterUrl = value;
                }
                if (property.equals("protocol")) {
                    filterType = value;
                }
                if (property.equals("user")) {
                    filterUser = value;
                }
                if (property.equals("description")) {
                    filterDescription = value;
                }
            }
        }

        Session sess = (Session) Stripersist.getEntityManager().getDelegate();
        Criteria c = sess.createCriteria(Geoservice.class);

        /*
         * Sorting is delivered by the frontend as two variables: sort which
         * holds the column name and dir which holds the direction (ASC, DESC).
         */
        if (sort != null && dir != null) {
            Order order = null;
            if (dir.equals("ASC")) {
                order = Order.asc(sort);
            } else {
                order = Order.desc(sort);
            }
            order.ignoreCase();
            c.addOrder(order);
        }

        if (filterName != null && filterName.length() > 0) {
            Criterion nameCrit = Restrictions.ilike("name", filterName, MatchMode.ANYWHERE);
            c.add(nameCrit);
        }
        if (filterUrl != null && filterUrl.length() > 0) {
            Criterion urlCrit = Restrictions.ilike("url", filterUrl, MatchMode.ANYWHERE);
            c.add(urlCrit);
        }
        if (filterType != null && filterType.length() > 0) {
            Criterion protocolCrit = Restrictions.ilike("type", filterType, MatchMode.ANYWHERE);
            c.add(protocolCrit);
        }
        if (filterDescription != null && filterDescription.length() > 0) {
            Criterion protocolCrit = Restrictions.ilike("description", filterDescription, MatchMode.ANYWHERE);
            c.add(protocolCrit);
        }
        if (filterUser != null && filterUser.length() > 0) {
            Criterion protocolCrit = Restrictions.ilike("user.fullname", filterUser, MatchMode.ANYWHERE);
            c.add(protocolCrit);
        }

        int rowCount = c.list().size();

        c.setMaxResults(limit);
        c.setFirstResult(start);

        List<Geoservice> services = c.list();

        for (Geoservice geoservice : services) {
           
            JSONObject j = this.getGridRow(geoservice.getId().intValue(), geoservice.getName(), geoservice.getUrl(), geoservice.getType().getDescription());
            jsonData.put(j);
        }
        final JSONObject grid = new JSONObject();
        grid.put("totalCount", rowCount);
        grid.put("gridrows", jsonData);
        return new StreamingResolution("application/json") {
            @Override
            public void stream(HttpServletResponse response) throws Exception {
                response.getWriter().print(grid.toString());
            }
        };
    }

    private JSONObject getGridRow(int i, String name, String url, String type) throws JSONException {
        JSONObject j = new JSONObject();
        j.put("id", i);
        j.put("name", name);
        j.put("url", url);
        j.put("type", type);
        return j;
    }

    // <editor-fold desc="Getters and Setters">
    public Geoservice getService() {
        return service;
    }

    public void setService(Geoservice service) {
        this.service = service;
    }

    public JSONArray getFilter() {
        return filter;
    }

    public void setFilter(JSONArray filter) {
        this.filter = filter;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    // </editor-fold>
}
