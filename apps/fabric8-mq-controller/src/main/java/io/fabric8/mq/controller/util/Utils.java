/*
 *
 *  * Copyright 2005-2015 Red Hat, Inc.
 *  * Red Hat licenses this file to you under the Apache License, version
 *  * 2.0 (the "License"); you may not use this file except in compliance
 *  * with the License.  You may obtain a copy of the License at
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  * implied.  See the License for the specific language governing
 *  * permissions and limitations under the License.
 *
 */

package io.fabric8.mq.controller.util;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

public class Utils {

    /**
     * @param root
     * @param names - should be of the form name=foo,type=blah etc
     * @return
     */
    public static ObjectName getObjectName(ObjectName root, String... names) throws MalformedObjectNameException {
        return getObjectName(root.toString(), names);
    }

    /**
     * @param names - should be of the form name=foo,type=blah etc
     * @return
     */
    public static ObjectName getObjectName(String domain, String... names) throws MalformedObjectNameException {
        String string = "";
        for (int i = 0; i < names.length; i++) {
            string += names[i];
            if ((i + 1) < names.length) {
                string += ",";
            }
        }
        String name = getOrderedProperties(getProperties(string));
        ObjectName objectName = new ObjectName(domain + ":" + name);
        return objectName;
    }

    public static Hashtable<String, String> getProperties(String string) {
        System.err.println("PROPS = " + string);
        Hashtable<String, String> result = new Hashtable<>();
        String[] props = string.split(",");
        for (int i = 0; i < props.length; i++) {
            String[] keyValues = props[i].split("=");
            result.put(keyValues[0].trim(), keyValues[1].trim());
        }
        return result;
    }

    public static String getOrderedProperties(Hashtable<String, String> properties) {
        TreeMap<String, String> map = new TreeMap<>(properties);
        String result = "";
        String separator = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result += separator;
            result += entry.getKey() + "=" + entry.getValue();
            separator = ",";
        }
        return result;
    }
}
