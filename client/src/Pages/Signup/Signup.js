import styled from 'styled-components';
import Google from '../../Image/Google'
import GitHub from '../../Image/GitHub'
import OAuthButton from './OAuthButton';
import SignupExplain from './SignupExplain';
import SignupForm from './SignupForm';


const Container = styled.div`
  display: flex;
  justify-content: center;
  max-width: 100%;
  background-color: hsl(210,8%,95%);
  div.content{
    display: flex !important;
    justify-content: center !important;
    align-items: center !important;
    padding: 24px;
    width: 100%;
    max-width: 1264px;
    margin: 0;
    background-color: transparent;
    border-left: 0;
    border-right: 0;
    box-sizing: inherit;
  }
`
const FlexRight = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex-shrink: 0;
  margin-right: 20px;
  height: 1000px;
  div.button-group {
    display: flex !important;
    flex-direction: column;
    margin-bottom: 16px;
    max-width: 316px;
  }
`

const Signup = () => {
  return (
    <Container>
      <div className='content'>
        <SignupExplain />
        <FlexRight>
          <div className='button-group'>
            <OAuthButton color="hsl(210deg 8% 20%)" bg_color="hsl(0deg 0% 100%)" hv_color="hsl(210deg 8% 98%)" ac_color="hsl(210deg 8% 95%)">
              <Google /> 
              Sign up with Google
            </OAuthButton>
            <OAuthButton color="#fff" bg_color="hsl(210deg 8% 20%)" hv_color="hsl(210deg 8% 15%)" ac_color="hsl(210deg 8% 5%)">
              <GitHub /> 
              Sign up with GitHub
            </OAuthButton>
          </div>
          <SignupForm />
        </FlexRight>
      </div>
    </Container>
  )
}

export default Signup;