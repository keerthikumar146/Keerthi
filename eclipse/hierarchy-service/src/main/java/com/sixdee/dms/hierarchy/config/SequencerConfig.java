package com.sixdee.dms.hierarchy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sixdee.dms.hierarchy.utils.Sequencer;

/**
 * @author balu.s
 *
 */
@Configuration
public class SequencerConfig {

	@Bean
	public Sequencer seqGenerator() {
		Sequencer sequencer = new Sequencer();
		sequencer.calculateLimit();
		return sequencer;
	}
	
}
