import { useRef } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const Container = styled.div`
  background-color: white;
  padding: 24px;
  box-shadow: rgba(0, 0, 0, 0.5) 0px 10px 24px 0;
  border-radius: 7px;
  margin-bottom: 16px;
  max-width: 316px;
`
const Form = styled.form`
  display: flex;
  flex-direction: column;
  margin-top: -6px;
  div.input-box{
    display: flex;
    flex-direction: column;
    margin: 6px 0 6px 0;
    label {
      font-size: 1.15rem;
      font-weight: 600;
      margin: 2px 0 2px 0;
      padding: 0 2px 0 2px;
    }
    input {
      width: 100%;
      padding: 0.6em 0.7em;
      border: rgb(186, 191, 196) solid 1px;
      border-radius: 3px;
    }
    p {
      margin: 4px 0 140px 0;
      font-size: 12px;
      color: rgb(106, 115, 124);
    }
  }
  button {
    background: rgb(10, 149, 255);
    border: none;
    border-radius: 3px;
    padding: 10px;
    color: white;
    cursor: pointer;
  }
`

const SignupForm = () => {
  const displayNameRef = useRef('');
  const emailRef = useRef('');
  const passwordeRef = useRef('');
  const navigate = useNavigate();


  const onSignUpSubmitHandler = async () => {
    const jsonData = JSON.stringify({
      displayName: displayNameRef.current.value,
      email: emailRef.current.value,
      password:passwordeRef.current.value
    });

    if(displayNameRef === '' || emailRef === '' || passwordeRef === '') {
      alert("이메일이나 패스워드를 확인하세요");
      return
    }

    await axios
    .post("http://13.125.30.88:8080/members", jsonData)
    .then((res) => {
      alert("Signup");
      navigate("/login");
      // 현재 로그인 요청 보내면 로그인페이지 -> 회원가입 페이지로 되돌아옴
      // url에 인풋 데이터는 남아있는 상태로 돌아옴
    })
    .catch((err) => {
      console.log(err);
    })
  }

  return (
    <Container>
      <Form onSubmit={onSignUpSubmitHandler}>
        <div className='input-box'>
          <label htmlFor='display-name'>Display name</label>
          <input ref={displayNameRef} type='text' id='display-name' name='display-name' />
        </div>
        <div className='input-box'>
          <label htmlFor='email'>Email</label>
          <input ref={emailRef} type='text' id='email' name='email' />
        </div>
        <div className='input-box'>
          <label htmlFor='password'>Password</label>
          <input ref={passwordeRef} type='text' id='password' name='password'/>
          <p>Passwords must contain at least eight characters, including at least 1 letter and 1 number.</p>
        </div>
        <button>SignUp</button>
      </Form>
  </Container>
  )
}

export default SignupForm;