import styled from 'styled-components';

const Div = styled.div`
  margin-bottom: 128px;
  margin-right: 30px;
  max-width: 510px;
  h1 {
    margin-bottom: 32px;
    line-height: 27px;
  }
  div {
    display: flex;
    margin-bottom: 32px;
  }
  div.mini-font {
    font-size: 13px;
    color: rgb(106, 115, 124);
  }
`

const SignupExplain = () => {
  return (
    <Div>
      <h1>Join the Stack Overflow community</h1>
      <div>Get unstuck — ask a question</div>
      <div>Unlock new privileges like voting and commenting</div>
      <div>Save your favorite tags, filters, and jobs</div>
      <div>Earn reputation and badges</div>
      <div className='mini-font'>Collaborate and share knowledge with a private group for FREE.</div>
    </Div>
  )
}

export default SignupExplain;