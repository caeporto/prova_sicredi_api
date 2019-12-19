package com.dbserver.cepapp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

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
        JSONObject cepObj = JsonReader.readJsonFromUrl("https://viacep.com.br/ws/91060900/json/");
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
		boolean compare = assertJson(cepObj, expected);
		assertTrue(compare);
	}
	
	@Test
	public void consultaCepInexistente()
	{
		JSONObject cepObj = JsonReader.readJsonFromUrl("https://viacep.com.br/ws/99999999/json/");
		JSONObject expected = new JSONObject();
		expected.put("erro", true);
		boolean compare = assertJson(cepObj, expected);
		assertTrue(compare);
	}
	
	@Test
	public void consultaCepInvalido()
	{
		JSONObject cepObj = JsonReader.readJsonFromUrl("https://viacep.com.br/ws/99999.999/json/");
		JSONObject expected = new JSONObject();
		expected.put("httpError", 400);
		boolean compare = assertJson(cepObj, expected);
		assertTrue(compare);
	}
	
	@Test
	public void consultaVariosCeps()
	{
		JSONArray cepsObj = JsonReader.readJsonArrayFromUrl("https://viacep.com.br/ws/RS/Gravatai/Barroso/json/");
		
		JSONObject cepObj1 = cepsObj.getJSONObject(0);
		JSONObject expected1 = new JSONObject();
		expected1.put("cep", "94085-170");
		expected1.put("logradouro", "Rua Ari Barroso");
		expected1.put("complemento", "");
		expected1.put("bairro", "Morada do Vale I");
		expected1.put("localidade", "Gravataí");
		expected1.put("uf", "RS");
		expected1.put("unidade", "");
		expected1.put("ibge", "4309209");
		expected1.put("gia", "");
		boolean compare1 = assertJson(cepObj1, expected1);
		
		JSONObject cepObj2 = cepsObj.getJSONObject(1);
		JSONObject expected2 = new JSONObject();
		expected2.put("cep", "94175-000");
		expected2.put("logradouro", "Rua Almirante Barroso");
		expected2.put("complemento", "");
		expected2.put("bairro", "Recanto Corcunda");
		expected2.put("localidade", "Gravataí");
		expected2.put("uf", "RS");
		expected2.put("unidade", "");
		expected2.put("ibge", "4309209");
		expected2.put("gia", "");
		boolean compare2 = assertJson(cepObj2, expected2);
		
		assertTrue(compare1 && compare2);
	}
}
