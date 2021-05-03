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
    public final StringExpression modelo;
    public final NumericExpression<Double> horometro;
    public final NumericExpression<Double> horasProximoMantenimiento;
    public final ObjectExpression<java.time.LocalDate> fechaUltimoMantenimiento;
    public final StringExpression notes;

    public QEquipo(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.denominacion = new StringExpressionImpl(this, "denominacion");
        this.modelo = new StringExpressionImpl(this, "modelo");
        this.horometro = new NumericExpressionImpl<Double>(this, "horometro");
        this.horasProximoMantenimiento = new NumericExpressionImpl<Double>(this, "horasProximoMantenimiento");
        this.fechaUltimoMantenimiento = new ObjectExpressionImpl<java.time.LocalDate>(this, "fechaUltimoMantenimiento");
        this.notes = new StringExpressionImpl(this, "notes");
    }

    public QEquipo(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.denominacion = new StringExpressionImpl(this, "denominacion");
        this.modelo = new StringExpressionImpl(this, "modelo");
        this.horometro = new NumericExpressionImpl<Double>(this, "horometro");
        this.horasProximoMantenimiento = new NumericExpressionImpl<Double>(this, "horasProximoMantenimiento");
        this.fechaUltimoMantenimiento = new ObjectExpressionImpl<java.time.LocalDate>(this, "fechaUltimoMantenimiento");
        this.notes = new StringExpressionImpl(this, "notes");
    }
}
