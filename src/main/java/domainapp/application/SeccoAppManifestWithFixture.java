package domainapp.application;

import domainapp.dom.impl.RecreateEquipos;
import org.apache.isis.applib.AppManifestAbstract2;

public class SeccoAppManifestWithFixture extends AppManifestAbstract2 {

    public static final Builder BUILDER =
            SeccoAppManifest.BUILDER
                    .withFixtureScripts(RecreateEquipos.class);

    public SeccoAppManifestWithFixture() {
        super(BUILDER);
    }

}
