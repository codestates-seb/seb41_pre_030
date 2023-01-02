import styled from 'styled-components';

const Button = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 316px;
  margin: 4px 0 4px 0;
  padding: 10.4px;
  border: 1px solid hsl(205deg 41% 63%);
  border-radius: 4px;
  font-weight: 500;

  color: ${props => props.color || "white"};
  background-color: ${props => props.bg_color || "white"};
  border-color: hsl(210deg 8% 85%);

  :hover {
    background: ${props => props.hv_color};
  }

  :active {
    background: ${props => props.ac_color};
  }

  cursor: pointer;

  svg {
    margin: 0 5px;
  }
`

const OAuthButton = (props) => {
  return (
    <Button color={props.color} bg_color={props.bg_color} hv_color={props.hv_color} ac-ac_color={props.ac_color}>
      {props.children}
    </Button>
  )
}
export default OAuthButton;