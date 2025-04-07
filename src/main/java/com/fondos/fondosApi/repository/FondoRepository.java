package com.fondos.fondosApi.repository;

import com.fondos.fondosApi.model.Fondo;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;

import java.util.*;

@Repository
public class FondoRepository implements IFondoRepository {

    private final DynamoDbTable<Fondo> fondoTable;

    public FondoRepository(DynamoDbEnhancedClient enhancedClient) {
        this.fondoTable = enhancedClient.table("Fondos", TableSchema.fromBean(Fondo.class));
    }

    @Override
    public Fondo save(Fondo fondo) {
        fondoTable.putItem(fondo);
        return fondo;
    }

    @Override
    public Optional<Fondo> findById(String id) {
        return Optional.ofNullable(fondoTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    @Override
    public List<Fondo> findAll() {
        List<Fondo> fondos = new ArrayList<>();
        fondoTable.scan().items().forEach(fondos::add);
        return fondos;
    }
}
