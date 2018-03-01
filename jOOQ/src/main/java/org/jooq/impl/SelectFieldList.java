/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.jooq.impl;

import java.util.Collection;

import org.jooq.Context;
import org.jooq.SelectFieldOrAsterisk;

/**
 * @author Lukas Eder
 */
final class SelectFieldList<F extends SelectFieldOrAsterisk> extends QueryPartList<F> {

    private static final long serialVersionUID = 8850104968428500798L;

    SelectFieldList() {
        super();
    }

    SelectFieldList(Collection<? extends F> wrappedList) {
        super(wrappedList);
    }

    SelectFieldList(F[] wrappedList) {
        super(wrappedList);
    }

    SelectFieldList(boolean qualify) {
        super(qualify);
    }

    SelectFieldList(Collection<? extends F> wrappedList, boolean qualify) {
        super(wrappedList, qualify);
    }

    SelectFieldList(F[] wrappedList, boolean qualify) {
        super(wrappedList, qualify);
    }

    @Override
    protected void toSQLEmptyList(Context<?> ctx) {
        ctx.visit(AsteriskImpl.INSTANCE);
    }

    @Override
    public final boolean declaresFields() {
        return true;
    }
}
