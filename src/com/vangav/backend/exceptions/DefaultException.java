/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * no license, I know you already got more than enough to worry about
 * keep going, never give up
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.backend.exceptions;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * BadRequestException represents exceptions caused by API calls
 * */
public class DefaultException extends VangavException {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -7397066872910847705L;

  /**
   * @return returns the exception type
   * */
  @Override
  public ExceptionType getExceptionType () {
    
    return ExceptionType.DEFAULT_EXCEPTION;
  }

  /**
   * Constructor BadRequestException.java
   * @param exception
   * @return new BadRequestException.java Object
   */
  public DefaultException () {
    
    super();
  }
}