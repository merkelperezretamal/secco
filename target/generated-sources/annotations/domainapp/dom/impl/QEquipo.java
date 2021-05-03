package domainapp.dom.impl;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QEquipo extends PersistableExpressionImpl<Equipo> implements PersistableExpression<Equipo>
{
    public static final QEquipo jdoCandidate = candidate("this");

    public static QEquipo candidate(String name)
    {
        return new QEquipo(null, name, 5);
    }

    public static QEquipo candidate()
    {
        return jdoCandidate;
    }

    public static QEquipo parameter(String name)
    {
        return new QEquipo(Equipo.class, name, ExpressionType.PARAMETER);
    }

    public static QEquipo variable(String name)
    {
        return new QEquipo(Equipo.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;
    public final StringExpression notes;

    public QEquipo(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
        this.notes = new StringExpressionImpl(this, "notes");
    }

    public QEquipo(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
        this.notes = new StringExpressionImpl(this, "notes");
    }
}
