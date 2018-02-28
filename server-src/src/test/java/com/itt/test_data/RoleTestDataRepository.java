package com.itt.test_data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.kmt.models.Role;
import com.itt.mock.ResourceFileLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@Slf4j
@Configuration
public class RoleTestDataRepository {
    private static HashMap<String, Role> roles;

    public RoleTestDataRepository() {
    }

    public synchronized HashMap<String, Role> getRoles() {

        if (roles == null) {
            roles = getTestDataMap(Role.class, "RoleTestData.json");
        }
        return roles;
    }

    /**
     * Parses the test data file and returns a HashMap<String, T> format where T
     * represents the type of object and the String is the key or name of the
     * object.
     *
     * @param classT class of Type T.
     * @return HashMap containing HashMap of T type of objects
     */
    public <T> HashMap<String, T> getTestDataMap(final Class<T> classT, final String testDataFileName) {

        HashMap<String, T> items = new HashMap<String, T>();

        try {

            String testData = ResourceFileLoader.loadResource(testDataFileName);

            ObjectMapper mapper = new ObjectMapper();
            HashMap testDataMap = mapper.readValue(testData, HashMap.class);
            Set keys = testDataMap.keySet();

            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String name = (String) iterator.next();
                T item = getObjectT(testDataMap, name, classT);
                items.put(name, item);
                log.info("Data:" + item.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error loading book data", ex);
        }
        return items;
    }

    /**
     * Retrieves an object of type T from the generic Hashmap given it's key
     *
     * @param testDataMap the generic hashmap containing the test data.
     * @param key name of the key
     * @param classT class of Object type T
     * @return T object
     * @throws JsonProcessingException
     * @throws IOException
     */
    private <T> T getObjectT(final HashMap testDataMap, final String key, final Class<T> classT)
            throws JsonProcessingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        HashMap map = (HashMap) testDataMap.get(key);
        String finalJson = mapper.writeValueAsString(map);
        T item = mapper.readValue(finalJson, classT);
        return item;
    }
}
