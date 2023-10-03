package com.example.msusers.domain.repositories;

import com.example.msusers.configuration.feign.OAuthFeignConfiguration;
import com.example.msusers.domain.models.Bill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-bill", url = "http://localhost:8082/", configuration = OAuthFeignConfiguration.class)
public interface IBillsFeignRepository {
	@GetMapping("/bills/perUser/{userId}")
	List<Bill> getAllBillsByUserId(@PathVariable String userId);
}
