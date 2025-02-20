package api_testing.jvega.restassured;

import org.junit.*;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class covid19_pais_varias_provincias {

    //variables del encabezado
    String key;
    String host;


    //parametros
    String name;
    String date;

    @Before
    public void init(){
        baseURI = "https://covid-19-data.p.rapidapi.com";

        //parametros
        this.name = "USA";
        this.date = "2020-04-01";

        //encabezados
        this.key = "65c3a8ec1emsh38db88917d7c333p1cd20ajsn923976797f86";
        this.host = "covid-19-data.p.rapidapi.com";


    }

    @Test
    public void ATC_Impresion_Respuesta()
    {
        String respuesta = given().header("x-rapidapi-key",this.key).
                and().header("x-rapidapi-host",this.host).
                param("name",this.name).
                param("date", this.date).
                get("/report/country/name").asPrettyString();

        System.out.println(respuesta);
    }

    @Test
    public void ATC_Respuesta_Valida()
    {
                given().header("x-rapidapi-key",this.key).
                and().header("x-rapidapi-host",this.host).
               param("name",this.name).
                param("date", this.date).
                when().
                get("/report/country/name").
             then().assertThat().statusCode(200);

    }


    @Test
    public void ATC_Propieddes_del_mensaje()
    {
        given().header("x-rapidapi-key",this.key).
                and().header("x-rapidapi-host",this.host).
                param("name",this.name).
                param("date", this.date).
                when().
                get("/report/country/name").
                then().assertThat().statusCode(200).
                body("[0]", hasKey("country")).
                body("[0]", hasKey("provinces")).
                body("[0]", hasKey("latitude")).
                body("[0]", hasKey("longitude")).
                body("[0]", hasKey("date"));
    }

    @Test
    public void ATC_Pais_Provincia_Dato_Exacto()
    {
        given().header("x-rapidapi-key",this.key).
                and().header("x-rapidapi-host",this.host).
                param("name",this.name).
                param("date", this.date).
                when().
                get("/report/country/name").
                then().assertThat().statusCode(200).
                body("[0].country", equalTo("USA")).
                body("[0].provinces[1].province", equalTo("Alaska")).
                body("[0].provinces[1].confirmed", equalTo(132));

    }

    @Test
    public void ATC_Respuesta_Json()
    {
        given().header("x-rapidapi-key",this.key).
                and().header("x-rapidapi-host",this.host).
                param("name",this.name).
                param("date", this.date).
                when().
                get("/report/country/name").
                then().assertThat().statusCode(200).
                header("Content-type","application/json");

    }



}
