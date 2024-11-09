# Circuit Breaker Microservices with Spring Boot

This project demonstrates the **Circuit Breaker** pattern in Spring Boot microservices using **Resilience4j**. The circuit breaker pattern helps manage and handle failures between interdependent microservices, improving resilience and stability.

## Overview

The project includes two main services:
- **Order Service**: Fetches address information from the Address Service.
- **Address Service**: Runs on port `1010` and provides address information for orders.

If the Address Service is down or fails to respond, the circuit breaker in the Order Service activates a **fallback method** to handle the failure gracefully. This fallback method outlines the health status and recovery behavior after a certain number of failed requests, explaining each state of the circuit breaker.

## Circuit Breaker Definition and States

The circuit breaker has three main states:
1. **Closed**: The circuit is working normally, and requests are routed to the Address Service on port `1010`.
2. **Open**: If the failure rate exceeds 50% over the last 10 requests, the circuit breaker opens, blocking further calls to the Address Service for a defined period (5 seconds).
3. **Half-Open**: After the waiting period, the circuit breaker moves to a half-open state. During this state, it tests if the Address Service has recovered by allowing up to 3 requests. If they succeed, the circuit closes; otherwise, it reopens.

## Configuration

The project configuration uses **Resilience4j** settings for managing the circuit breaker behavior.

### `application.yml`

```yaml
management:
  endpoint:
    health:
      show-details: always
  endpoints:
    web:
      exposure:
        include: health
  health:
    circuitbreakers:
      enabled: true

resilience4j:
  circuitbreaker:
    instances:
      order-service:
        sliding-window-type: COUNT_BASED
        failure-rate-threshold: 50 # Circuit breaker opens when 50% of calls fail
        minimum-number-of-calls: 5 # Minimum calls before failure rate calculation
        automatic-transition-from-open-to-half-open-enabled: true
        wait-duration-in-open-state: 5s # Time before transitioning from open to half-open
        permitted-number-of-calls-in-half-open-state: 3 # Number of test calls in half-open
        sliding-window-size: 10 # Calls within the sliding window
        register-health-indicator: true
