package domainapp.dom.impl;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QMotor extends PersistableExpressionImpl<Motor> implements PersistableExpression<Motor>
{
    public static final QMotor jdoCandidate = candidate("this");

    public static QMotor candidate(String name)
    {
        return new QMotor(null, name, 5);
    }

    public static QMotor candidate()
    {
        return jdoCandidate;
    }

    public static QMotor parameter(String name)
    {
        return new QMotor(Motor.class, name, ExpressionType.PARAMETER);
    }

    public static QMotor variable(String name)
    {
        return new QMotor(Motor.class, name, ExpressionType.VARIABLE);
    }

    public final domainapp.dom.impl.QEquipo equipo;
    public final StringExpression tag;

    public QMotor(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        if (depth > 0)
        {
            this.equipo = new domainapp.dom.impl.QEquipo(this, "equipo", depth-1);
        }
        else
        {
            this.equipo = null;
        }
        this.tag = new StringExpressionImpl(this, "tag");
    }

    public QMotor(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.equipo = new domainapp.dom.impl.QEquipo(this, "equipo", 5);
        this.tag = new StringExpressionImpl(this, "tag");
    }
}
