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


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.datanucleus.query.typesafe.TypesafeQuery;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "secco.Equipos"
)
public class Equipos {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @MemberOrder(sequence = "1")
    public Equipo cargar(
            @Parameter(maxLength = 40)
            final String denominacion,
            @Parameter(maxLength = 40)
            final double horometro,
            @Parameter(maxLength = 40)
            final double rpm,
            @Parameter(maxLength = 40)
            final double presionAceite) {
        return repositoryService.persist(new Equipo(denominacion, horometro, rpm, presionAceite));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "2")
    public List<Equipo> buscarPorDenominacion(
            @Parameter(maxLength = 40)
            final String name) {
        TypesafeQuery<Equipo> q = isisJdoSupport.newTypesafeQuery(Equipo.class);
        final QEquipo cand = QEquipo.candidate();
        q = q.filter(
                cand.denominacion.indexOf(q.stringParameter("name")).ne(-1)
        );
        return q.setParameter("name", name)
                .executeList();
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @MemberOrder(sequence = "3")
    public List<Equipo> listarTodos() {
        return repositoryService.allInstances(Equipo.class);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
