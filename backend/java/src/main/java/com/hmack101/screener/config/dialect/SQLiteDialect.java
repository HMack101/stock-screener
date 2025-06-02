package com.hmack101.screener.config.dialect;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.pagination.LimitOffsetLimitHandler;
import org.hibernate.engine.jdbc.env.spi.NameQualifierSupport;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.type.*;
import org.hibernate.type.spi.TypeConfiguration;


public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();
    }

    @Override
    public NameQualifierSupport getNameQualifierSupport() {
        return NameQualifierSupport.NONE;
    }

    @Override
    public LimitOffsetLimitHandler getLimitHandler() {
        return LimitOffsetLimitHandler.INSTANCE;
    }

    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public boolean supportsValuesList() {
        return true;
    }

    @Override
    public boolean supportsUnionAll() {
        return true;
    }

    @Override
    public boolean supportsInsertReturning() {
        return false;
    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        TypeConfiguration typeConfig = typeContributions.getTypeConfiguration();

        // Example: Convert BasicTypeReference<Integer> to BasicType
        BasicType<?> integerType = typeConfig.getBasicTypeRegistry().resolve(new BasicTypeReference<Integer>() {
            @Override
            public String getName() {
                return "integer";
            }
        });
        typeContributions.contributeType(integerType);
        typeContributions.contributeType(StandardBasicTypes.INTEGER);

        typeContributions.contributeType(StandardBasicTypes.INTEGER);
        // Use BasicType directly — no need for SqlTypes or BasicTypeReference
        registerBasicType(typeContributions, );
        registerBasicType(typeContributions, StandardBasicTypes.STRING);
        registerBasicType(typeContributions, StandardBasicTypes.BIG_INTEGER);
        registerBasicType(typeContributions, StandardBasicTypes.BOOLEAN);
        registerBasicType(typeContributions, StandardBasicTypes.FLOAT);
        registerBasicType(typeContributions, StandardBasicTypes.DOUBLE);
        registerBasicType(typeContributions, StandardBasicTypes.DATE);
        registerBasicType(typeContributions, StandardBasicTypes.TIMESTAMP);
    }

    private void registerBasicType(TypeContributions contributions, BasicType<?> type) {
        contributions.contributeType(type);
    }
}
