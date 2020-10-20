package com.br.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

import br.com.entidades.Logradouro;

/**************************************************************************************************
 * Descrição: classe de serviço que faz a busca dos dados do logradouro pelo cep via web service
 * Autor: Rafael Leonardo de Lima
 * Data: 12/10/2020
 * ***********************************************************************************************/

public class CepService {
	
	public static Logradouro pesquisaCep(String cep) {
		Logradouro logradouro = null;
		try {
			URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
			URLConnection connection = url.openConnection();
			InputStream inputStream = connection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String atributosCep = "";
			StringBuilder jsonCep = new StringBuilder();
			while ((atributosCep = bufferedReader.readLine()) != null) {
				jsonCep.append(atributosCep);
			}
			logradouro = new Gson().fromJson(jsonCep.toString(), Logradouro.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logradouro;
	}

}
