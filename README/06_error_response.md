# [Error Response](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java)

+ Normally a Vangav Backend Service will return the expected response (JSON, HTML or FILE) as defined per-controller with a status code of 200 (HTTP_OK).
+ Other than 200 (HTTP_OK), Vangav Backend may also return 400 (HTTP_BAD_REQUEST) and 500 (HTTP_INTERNAL_SERVER_ERROR).
  + 400 (HTTP_BAD_REQUEST) in case the problem came from the client's request. e.g.: expired access token, wrong login, missing/invalid mandatory request param, etc ...
  + 500 (HTTP_INTERNAL_SERVER_ERROR) in case of a problem within the service itself. e.g.: database unreachable, a utility method invoked using bad arguments, etc ...
+ Returned error response (for both 400 (`BAD_REQUEST_EXCEPTION`) and 500 (`CODE_EXCEPTION`/`DEFAULT_EXCEPTION`)) has the following JSON structure. By default all elements are returned to the client, each element can be switched on/off from [response_error_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop). The returned error JSON structure always includes all elements, switched off elements have empty/default values.

  | element_name | element_type | explanation |
  | ------------ | ------------ | ----------- |
  | [`error_type`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L21) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L196) | [`BAD_REQUEST_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L51), [`CODE_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L52) or [`DEFAULT_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L53). `DEFAULT_EXCEPTION` indicates a non-vangav-exception, i.e. an exception that should have been handled properley but it's not. |
  | [`error_code`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L25) | [`int`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L198) | Optionally set this value while throwing an exception. Values 0 through 299 are reserved for use by Vangav Backend functionalities/utilities. |
  | [`error_sub_code`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L29) | [`int`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L200) | Optionally set this value while throwing an exception. |
  | [`error_message`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L33) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L202) | Exception's message. Defined for each thrown Vangav Exception. |
  | [`error_class`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L37) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L204) | One of [`ExceptionClass`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L67) enum values, as defined per-thrown Vangav Exception. |
  | [`error_stack_trace`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L41) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L206) | Exception's stack trace. |
  | [`error_trace_id`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L45) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L208) | Binds the error's Exception with the request causing it through the request_id. |
  
+ While implementing your Vangav Backend Service's logic, throw a [BadRequestException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/BadRequestException.java) to return an error response with status code 400 (HTTP_BAD_REQUEST) and throw a [CodeException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/CodeException.java) to return an error response with status code 500 (HTTP_INTERNAL_SERVER_ERROR). All request-processing (before and after response) are caught and handled in [ParentPlayHandler](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java) where request processing starts at [`handleRequestAsync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L213) which gets called from controller's Controller class (e.g.: [`handlerReverseGeoCode.handleRequestAsync(request() );`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java#L49)).

+ An example for throwing a bad request exception can be found [here](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1010)

```java
throw new BadRequestException(
  "Invalid param ["
    + name
    + "]",
  ExceptionClass.INVALID);
```

+ An example for throwing a code exception can be found [here](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java#L105)

```java
throw new CodeException(
  "propterty ["
    + DispatcherProperties.kWorkersTopology
    + "] isn't defined in properties file ["
    + DispatcherProperties.i().getName()
    + "]",
  ExceptionClass.PROPERTIES);
```