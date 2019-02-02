package ba.fit.bookdiary.data;

import java.io.Serializable;

public class AutentifikacijaResultVM implements Serializable
{
    public String username;
    public String ime;
    public String prezime;
    public String token;
    public String email;
    public Integer korisnickiNalogId;
}
