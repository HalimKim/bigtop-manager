/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.bigtop.manager.dao.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "service_config")
public class ServiceConfigPO extends BasePO implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Config file name, eg: zookeeper-env, hdfs-site
     */
    @Column(name = "name")
    private String name;

    /**
     * Properties json, represents by key-value pair belongs to a config file
     */
    @Column(name = "properties_json")
    private String propertiesJson;

    @Column(name = "cluster_id")
    private Long clusterId;

    @Column(name = "service_id")
    private Long serviceId;

    @Transient
    @Column(name = "service_name")
    private String serviceName;
}
