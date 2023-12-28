package org.stafloker.data.repositories.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository //@Profile("dev")
public class DatabaseSeederServiceDev {
    private final UserSeederDev userSeederDev;
    private final SpmSeederServiceDev spmSeederServiceDev;

    @Autowired
    public DatabaseSeederServiceDev(UserSeederDev userSeederDev, SpmSeederServiceDev spmSeederServiceDev) {
        this.userSeederDev = userSeederDev;
        this.spmSeederServiceDev = spmSeederServiceDev;
        this.reSeedDatabase();
    }

    public void seedDatabase() {
        this.userSeederDev.seedDataBase();
        this.spmSeederServiceDev.seedDatabase();
    }

    public void deleteAll() {
        this.userSeederDev.deleteAllAndInitialize();
        this.spmSeederServiceDev.deleteAll();
    }

    public void reSeedDatabase() {
        this.deleteAll();
        this.seedDatabase();
    }
}