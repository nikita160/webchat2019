package model;

import dbservise.ConnectionBuilder;

public interface ConnectionBuilderFactory {
    ConnectionBuilder getConnectionBuilder();
}
