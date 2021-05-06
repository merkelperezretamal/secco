package domainapp.dom.impl;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import javax.inject.Inject;

public class RecreateEquipos extends FixtureScript {
    public RecreateEquipos() {
        super(null, null, Discoverability.DISCOVERABLE);
    }
    @Override
    protected void execute(final ExecutionContext ec) {
        isisJdoSupport.deleteAll(Equipo.class);
        ec.addResult(this,
                this.equipos.cargar("E001", 0, 2200, 40));
        ec.addResult(this,
                this.equipos.cargar("E003", 24, 2500, 35));
        ec.addResult(this,
                this.equipos.cargar("E002", 48, 2350, 38));
    }
    @Inject
    Equipos equipos;
    @Inject
    IsisJdoSupport isisJdoSupport;
}
