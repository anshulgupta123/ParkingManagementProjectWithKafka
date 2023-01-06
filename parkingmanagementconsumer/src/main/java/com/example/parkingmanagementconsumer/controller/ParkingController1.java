package com.example.parkingmanagementconsumer.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController1 {

	private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

	public ParkingController1(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
		this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
	}

	@GetMapping("/v1/getparkingDetails")
	public ResponseEntity<String> getParkingSpace() {
		KafkaStreams kafkaStreams = streamsBuilderFactoryBean.getKafkaStreams();
		ReadOnlyKeyValueStore<String, Long> parkingSpace = kafkaStreams
				.store(StoreQueryParameters.fromNameAndType("parking_space_1", QueryableStoreTypes.keyValueStore()));
		System.out.println(parkingSpace.get("variable"));
		return new ResponseEntity<>( "No of Parking Avialable is" + parkingSpace.get("variable"),
				HttpStatus.OK);
	}
}
