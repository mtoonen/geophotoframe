Ext.onReady(function() {
    // Default grid config
    var defaultGridConfig = {
        autoWidth: true,
        height: '100%',
        disableSelection: false,
        loadMask: true,
        viewConfig: {
            trackOver: true,
            stripeRows: true
        }
    };
    Ext.define('TableRow', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'id', type: 'int'},
            {name: 'description', type: 'string'},
            {name: 'name', type: 'string'},
            {name: 'url', type: 'string'},
            {name: 'user', type: 'string'},
            {name: 'type', type: 'string'}
        ]
    });

    var store = Ext.create('Ext.data.Store', {
        pageSize: 10,
        model: 'TableRow',
        remoteSort: true,
        remoteFilter: true,
        proxy: {
            type: 'ajax',
            url: gridurl,
            reader: {
                type: 'json',
                root: 'gridrows',
                totalProperty: 'totalCount'
            },
            simpleSortMode: true
        },
        listeners: {
            load: function() {
                // Fix to apply filters
                Ext.getCmp('editGrid').doLayout();
            }
        }
    });

    var grid = Ext.create('Ext.grid.Panel', Ext.merge(defaultGridConfig, {
        id: 'editGrid',
        store: store,
        columns: [
            {
                id: 'name',
                text: "Name",
                dataIndex: 'name',
                flex: 1,
                filter: {
                    xtype: 'textfield'
                }
            },
            {
                id: 'description',
                text: "Description",
                dataIndex: 'description',
                flex: 1,
                filter: {
                    xtype: 'textfield'
                }
            }, {
                id: 'url',
                text: "URL",
                dataIndex: 'url',
                flex: 1,
                filter: {
                    xtype: 'textfield'
                }
            }, {
                id: 'type',
                text: "Type",
                dataIndex: 'type',
                flex: 1,
                filter: {
                    xtype: 'textfield'
                }
            },
            {
                id: 'user',
                text: "User",
                dataIndex: 'user',
                flex: 1,
                filter: {
                    xtype: 'textfield'
                }
            }, {
                id: 'edit',
                header: '',
                dataIndex: 'id',
                flex: 1,
                sortable: false,
                hideable: false,
                menuDisabled: true/*,
                renderer: function(value) {
                    return Ext.String.format('<a href="' + editattributesurl + '?featureSourceId={0}">Attributen bewerken</a>', value) +
                            ' | ' +
                            Ext.String.format('<a href="#" onclick="return editObject(\'{0}\');">Bewerken</a>', value) +
                            ' | ' +
                            Ext.String.format('<a href="#" onclick="return removeObject(\'{0}\');">Verwijderen</a>', value);
                }*/,
                sortable: false
            }
        ],
        bbar: Ext.create('Ext.PagingToolbar', {
            store: store,
            displayInfo: true,
            displayMsg: 'Attribuutbron {0} - {1} of {2}',
            emptyMsg: "Geen attribuutbronnen weer te geven"
        }),
        plugins: [
            Ext.create('Ext.ux.grid.GridHeaderFilters', {
                enableTooltip: false
            })
        ],
        renderTo: 'grid-container',
        listeners: {
            afterrender: function(grid) {
                // Default sort on second column
                grid.columns[1].setSortState('ASC');
            }
        }
    }));
});