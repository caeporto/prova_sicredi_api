package com.dbserver.cepapp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.dbserver.cepapp.util.JsonReader;

public class AppTest 
{
	
	private boolean assertJson(JSONObject obj, JSONObject expected)
	{
		if(obj != null)
			return obj.similar(expected);
		return false;
	}
	
    @Test
    public void consultaCepValido()
    {
        JSONObject cep_obj = JsonReader.readJsonFromUrl("https://viacep.com.br/ws/91060900/json/");
        JSONObject expected = new JSONObject();
		expected.put("cep", "91060-900");
		expected.put("logradouro", "Avenida Assis Brasil 3940");
		expected.put("complemento", "");
		expected.put("bairro", "São Sebastião");
		expected.put("localidade", "Porto Alegre");
		expected.put("uf", "RS");
		expected.put("unidade", "");
		expected.put("ibge", "4314902");
		expected.put("gia", "");
		boolean compare = assertJson(cep_obj, expected);
		assertTrue(compare);
	}
	
	@Test
	public void consultaCepInexistente()
	{
		JSONObject cep_obj = JsonReader.readJsonFromUrl("https://viacep.com.br/ws/99999999/json/");
		JSONObject expected = new JSONObject();
		expected.put("erro", true);
		boolean compare = assertJson(cep_obj, expected);
		assertTrue(compare);
	}
	
	@Test
	public void consultaCepInvalido()
	{
		JSONObject cep_obj = JsonReader.readJsonFromUrl("https://viacep.com.br/ws/99999.999/json/");
		JSONObject expected = new JSONObject();
		expected.put("httpError", 400);
		boolean compare = assertJson(cep_obj, expected);
		assertTrue(compare);
	}
}
