/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.hk2.classmodel.reflect.impl;

import org.glassfish.hk2.classmodel.reflect.ParameterizedInterfaceModel;

import java.util.*;
import java.util.stream.Collectors;
import org.glassfish.hk2.classmodel.reflect.ExtensibleType;

/**
 * Implementation of the {@link ParameterizedInterfaceModel}
 *
 * @author Jerome Dochez
 */
class ParameterizedInterfaceModelImpl implements ParameterizedInterfaceModel {

    final TypeProxy<ExtensibleType> rawInterface;
    final List<ParameterizedInterfaceModel> parameterizedTypes = new ArrayList<>();

    ParameterizedInterfaceModelImpl(TypeProxy<ExtensibleType> rawInterface) {
        this.rawInterface = rawInterface;
    }

    synchronized void addParameterizedType(ParameterizedInterfaceModel type) {
        parameterizedTypes.add(type);
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        sb.append(rawInterface.getName());
        if (!parameterizedTypes.isEmpty()) {
            sb.append("<");
            Iterator<ParameterizedInterfaceModel> parameterizedTypeItr = parameterizedTypes.iterator();
            while(parameterizedTypeItr.hasNext()) {
                sb.append(parameterizedTypeItr.next().getName());
                if (parameterizedTypeItr.hasNext()) sb.append(",");
            }
            sb.append(">");
        }
        return sb.toString();
    }

    public TypeProxy<? extends ExtensibleType> getRawInterfaceProxy() {
        return rawInterface;
    }

    @Override
    public ExtensibleType getRawInterface() {
        return rawInterface.get();
    }

    public String getRawInterfaceName() {
        return rawInterface.getName();
    }

    @Override
    public Collection<ParameterizedInterfaceModel> getParametizedTypes() {
        return Collections.unmodifiableCollection(parameterizedTypes);
    }

    @Override
    public String toString() {
        return rawInterface.getName() + parameterizedTypes.stream().map(Object::toString).collect(Collectors.joining(", ", "<", ">"));
    }

}
