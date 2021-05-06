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
import javax.jdo.annotations.Persistent;
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
@javax.jdo.annotations.Unique(name="Equipo_denominacion_UNQ", members = {"denominacion"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Equipo implements Comparable<Equipo> {

    public Equipo(final String denominacion,
                  final double horometro, final double rpm, final double presionAceite) {
        this.denominacion = denominacion;
        this.horometro = horometro;
        this.rpm = rpm;
        this.presionAceite = presionAceite;
    }

    public String title() {
        return getDenominacion();
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Getter @Setter
    private String denominacion;

    @javax.jdo.annotations.Column(allowsNull = "false")
//    @Property(hidden = Where.EVERYWHERE) //Oculta la propiedad (para que no se vea cuando se actualiza por ejemplo)
    @Getter @Setter
    private double horometro;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double porcentajeDisponibilidad;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double rpm;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double presionAceite;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String notes;

    @Persistent(
            mappedBy = "equipo",
            dependentElement = "true"
    )
    @Getter @Setter
    private Motor motor;

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Equipo actualizarHorometro(final double horometro) {
        setHorometro(horometro);
        return this;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Equipo actualizarRpm(final double rpm) {
        setRpm(rpm);
        return this;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Equipo actualizarPresionAceite(final double presionAceite) {
        setPresionAceite(presionAceite);
        return this;
    }

    public String default0UpdateName() {
        return getDenominacion();
    }
    public double default1UpdateName() {
        return getHorometro();
    }

//    @Property(notPersisted = true) //Agregado como propiedad derivada
//    public String getName() {
//        return getDenominacion();
//    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
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

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Motor nuevoMotor(final String tag) {
        return repositoryService.persist(new Motor(this, tag));
    }

    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            associateWith = "motors", associateWithSequence = "2"
    )
    public Equipo borrarMotor(Motor motor) {
        repositoryService.removeAndFlush(motor);
        return this;
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