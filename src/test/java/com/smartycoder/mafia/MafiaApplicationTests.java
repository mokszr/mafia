package com.smartycoder.mafia;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.smartycoder.mafia.entity.FileType;


class MafiaApplicationTests {

	@Test
	void sendMafiaJobRequest() throws IOException, JSONException {
		
//		 File file = new File("image1.jpg");
//         HttpPost httpPost = new HttpPost("http://localhost:8080/job-management/mafia-jobs");
//        
//         JSONObject manParms = new JSONObject();
//         manParms.put("scale", "0.3");
//         
//         JSONObject jsonObject = new JSONObject();
//         jsonObject.put("fileType", FileType.JPEG.name());
//         jsonObject.put("manipulationName", "scale_image");
//         jsonObject.put("manipulationParameters", manParms);
//         
//         StringBody userBody = new StringBody(jsonObject.toString(), ContentType.APPLICATION_JSON);
//         FileBody fileBody =   new FileBody(file, ContentType.DEFAULT_BINARY);
//
//         MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
//         entityBuilder.addPart("mafiaJob", userBody);
//         entityBuilder.addPart("file", fileBody);
//         HttpEntity entity = entityBuilder.build();
//         httpPost.setEntity(entity);
//
//         HttpClient client = HttpClientBuilder.create().build();
//         
//         HttpResponse response = client.execute(httpPost);
//         HttpEntity responseEntity = response.getEntity();
//
//         // print response
//         System.out.println(IOUtils.toString(responseEntity.getContent(), "utf8"));
 
		
	}

}
