package domainapp.dom.impl;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "motors" )
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="Motor_equipo_tag_UNQ", members = {"equipo","tag"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Motor implements Comparable<Motor> {

    public Motor(Equipo equipo, String tag){
        this.equipo = equipo;
        this.tag = tag;
    }

    public String title() {
        return String.format(
                "%s",
                getTag(), getEquipo().getDenominacion());
    }

    @javax.jdo.annotations.Column(allowsNull = "false", name = "equipoId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private Equipo equipo;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String tag;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private double temperaturaAceite;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private double temperaturaAgua;

    @Override
    public String toString() {
        return getTag();
    }

    @Override
    public int compareTo(final Motor other) {
        return ComparisonChain.start()
                .compare(this.getEquipo(), other.getEquipo())
                .compare(this.getTag(), other.getTag())
                .result();
    }
}
