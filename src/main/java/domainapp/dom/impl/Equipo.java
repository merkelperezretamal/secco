/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.impl;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "secco" )
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="Equipo_denominacion_modelo_UNQ", members = {"denominacion", "modelo"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Equipo implements Comparable<Equipo> {

    public Equipo(final String denominacion,
                  final String modelo,
                  final double horometro,
                  final double horasProximoMantenimiento,
                  final LocalDate fechaUltimoMantenimiento) {
        this.denominacion = denominacion;
        this.modelo = modelo;
        this.horometro = horometro;
        this.horasProximoMantenimiento = horasProximoMantenimiento;
        this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
    }

    public String title() {
        return getDenominacion() + ", " + getModelo();
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(hidden = Where.EVERYWHERE) //Oculta la propiedad (para que no se vea cuando se actualiza por ejemplo)
    @Getter @Setter
    private String denominacion;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private String modelo;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private double horometro;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private double horasProximoMantenimiento;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private LocalDate fechaUltimoMantenimiento;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String notes;


    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Equipo updateName(
            @Parameter(maxLength = 40)
            final String denominacion,
            @Parameter(maxLength = 40)
            final String modelo,
//            @Parameter(maxLength = 40)
            final double horometro,
            final LocalDate fechaUltimoMantenimiento) {
        setDenominacion(denominacion);
        setModelo(modelo);
        setHorometro(horometro);
        setFechaUltimoMantenimiento(fechaUltimoMantenimiento);
        return this;
    }
    public String default0UpdateName() {
        return getDenominacion();
    }
    public String default1UpdateName() {
        return getModelo();
    }
    public double default2UpdateName() {
        return getHorometro();
    }

    @Property(notPersisted = true) //Agregado como propiedad derivada
    public String getName() {
        return getDenominacion() + " " + getModelo();
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }

    @Override
    public String toString() {
        return getDenominacion();
    }

    @Override
    public int compareTo(final Equipo other) {
        return ComparisonChain.start()
                .compare(this.getDenominacion(), other.getDenominacion())
                .result();
    }


    @javax.jdo.annotations.NotPersistent
    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.jdo.annotations.NotPersistent
    @javax.inject.Inject
    TitleService titleService;

    @javax.jdo.annotations.NotPersistent
    @javax.inject.Inject
    MessageService messageService;


}