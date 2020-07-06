FROM jboss/wildfly:18.0.1.Final
ADD inventory-management/target/Inventory.war /opt/jboss/wildfly/standalone/deployments
