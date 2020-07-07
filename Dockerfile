FROM jboss/wildfly:18.0.1.Final
ADD target/Inventory.war /opt/jboss/wildfly/standalone/deployments
