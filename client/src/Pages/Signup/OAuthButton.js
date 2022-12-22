import styled from 'styled-components';

const Button = styled.button`
  width: 316px;
  margin: 4px 0 4px 0;
  padding: 10.4px;
  border-color: rgb(214, 217, 220);
  border-radius: 5px;

  color: ${props => props.color || "white"};
  background-color: ${props => props.bg_color || "white"};
`

const OAuthButton = (props) => {
  return (
    <Button color={props.color} bg_color={props.bg_color}>
      {props.children}
    </Button>
  )
}
export default OAuthButton;