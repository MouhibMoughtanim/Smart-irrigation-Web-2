package ma.emsi.smartwatering.api;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;

import java.util.List;

public class reponse {
    private AppUser user;
    private List<EspaceVert> espacesVerts;


    public reponse() {

    }


    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public List<EspaceVert> getEspacesVerts() {
        return espacesVerts;
    }

    public void setEspacesVerts(List<EspaceVert> espacesVerts) {
        this.espacesVerts = espacesVerts;
    }
}
