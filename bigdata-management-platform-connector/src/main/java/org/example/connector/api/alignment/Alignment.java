package org.example.connector.api.alignment;

import org.apache.commons.lang3.StringUtils;

public abstract class Alignment<A extends IColumn, B extends IColumn> {
    protected CompareFunc<A, B> compareFunc = (ai, bi, a, b) -> StringUtils.equals(a.getName(), b.getName()) && StringUtils.equals(a.getType(), b.getType());

    public void setCompareFunc(CompareFunc<A, B> compareFunc) {
        if (compareFunc != null) {
            this.compareFunc = compareFunc;
        }
    }

    public abstract AlignmentResult<A, B> align(A[] listA, B[] listB);
}

