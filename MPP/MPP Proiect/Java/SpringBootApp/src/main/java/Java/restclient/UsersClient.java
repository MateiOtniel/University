package Java.restclient;

import Java.restservices.ServiceException;
import model.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Callable;
import java.util.ArrayList;

public class UsersClient {
    public static final String URL = "http://localhost:8080/java/tests";

    private final RestTemplate restTemplate = new RestTemplate();

    private<T> T execute(Callable<T> callable) throws ServiceException{
        try {
            return callable.call();
        }catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Test[] getAll() throws ServiceException{
        return execute(() -> restTemplate.getForObject(URL, Test[].class));
    }

    public Test getById(int id) throws ServiceException{
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Test.class));
    }

    public Test save(Test test) throws ServiceException{
        return execute(() -> restTemplate.postForObject(URL, test, Test.class));
    }

    public void update(Test test) throws ServiceException{
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, test.getId()), test);
            return null;
        });
    }

    public void delete(int id) throws ServiceException{
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}
