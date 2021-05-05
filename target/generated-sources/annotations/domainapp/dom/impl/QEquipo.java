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

    public final StringExpression denominacion;
    public final NumericExpression<Double> horometro;
    public final StringExpression notes;
    public final domainapp.dom.impl.QMotor motor;

    public QEquipo(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.denominacion = new StringExpressionImpl(this, "denominacion");
        this.horometro = new NumericExpressionImpl<Double>(this, "horometro");
        this.notes = new StringExpressionImpl(this, "notes");
        if (depth > 0)
        {
            this.motor = new domainapp.dom.impl.QMotor(this, "motor", depth-1);
        }
        else
        {
            this.motor = null;
        }
    }

    public QEquipo(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.denominacion = new StringExpressionImpl(this, "denominacion");
        this.horometro = new NumericExpressionImpl<Double>(this, "horometro");
        this.notes = new StringExpressionImpl(this, "notes");
        this.motor = new domainapp.dom.impl.QMotor(this, "motor", 5);
    }
}
