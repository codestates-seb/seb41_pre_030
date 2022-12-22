import styled from 'styled-components';
import Logo from '../Image/Logo';

const FooterEl = styled.footer`
  width: 100vw;
  height: 322px;
  color: rgb(145, 153, 161);
  background-color: rgb(35, 38, 41);
  bottom: 0;
`
const FooterContainer = styled.div`
  display: flex;
  flex-flow: row wrap;
  max-width: 1264px;
  margin: 0 auto;
  padding: 32px 12px 12px 12px;
`
const FooterLogo = styled.div`
  flex: 0 0 64px;
  margin: -12px 0 32px 0;
`
const FooterNav = styled.nav`
  display: flex;
  flex: 2 1 auto;
  flex-wrap: wrap;
`
const FooterCol = styled.div`
  flex: 1 0 auto;
  padding: 0 12px 24px 0;
`
const FooterColTitle = styled.h5`
  text-transform: uppercase;
  font-weight: bold;
  margin-bottom: 12px;
  color: rgb(186, 191, 196);
  line-height: calc(17/13);
`
const FooterColList = styled.ul`
  list-style: none;
`
const FooterColListItem = styled.li`
  a {
    display: inline-block;
    padding: 4px 0;
    text-decoration: none;
    font-size: 13px;
    color: rgb(145, 153, 161);
    line-height: 17px;
    cursor: pointer;
  }
  display: list-item;
`
const FooterCopyright = styled.div`
  display: flex;
  flex: 1 1 150px;
  flex-direction: column;
  font-size: 11px;
  color: rgb(145, 153, 161);
  p {
    margin-top: auto;
    margin-bottom: 24px;
  }
`
const FooterCRList = styled.ul`
  display: flex;
  margin-bottom: 0;
  margin-left: 0;
  list-style: none;
`
const FooterCRListItem = styled.li`
  a {
    display: inline-block;
    padding: 4px 0 4px 0;
    text-decoration: none;
    font-size: 13px;
    color: rgb(145, 153, 161);
    line-height: 17px;
    cursor: pointer;
  }
  display: list-item;
  margin-left: ${props => props.margin || "12px"};
`

const Footer = () => {
  return (
    <FooterEl>
      <FooterContainer>
        <FooterLogo>
          <Logo />
        </FooterLogo>
        <FooterNav>
          <FooterCol>
            <FooterColTitle>stack overflow</FooterColTitle>
            <FooterColList>
              <FooterColListItem><a href="{() => false}">Questions</a></FooterColListItem>
              <FooterColListItem><a href="{() => false}">Help</a></FooterColListItem>
            </FooterColList>
          </FooterCol>
          <FooterCol>
            <FooterColTitle>product</FooterColTitle>
              <FooterColList>
                <FooterColListItem><a href="{() => false}">Teams</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Advertising</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Collectives</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Talent</a></FooterColListItem>
              </FooterColList>
          </FooterCol>
          <FooterCol>
            <FooterColTitle>company</FooterColTitle>
              <FooterColList>
                <FooterColListItem><a href="{() => false}">About</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Press</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Work Here</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Legal</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Privacy Policy</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Terms of Service</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Contact Us</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Cookie Settings</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Cookie Policy</a></FooterColListItem>
              </FooterColList>
          </FooterCol>
          <FooterCol>
            <FooterColTitle>stact exchange network</FooterColTitle>
              <FooterColList>
                <FooterColListItem><a href="{() => false}">Technology</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Cultures & recreation</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Life & arts</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Science</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Professional</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Business</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">API</a></FooterColListItem>
                <FooterColListItem><a href="{() => false}">Data</a></FooterColListItem>
              </FooterColList>
          </FooterCol>
        </FooterNav>
        <FooterCopyright>
          <FooterCRList>
            <FooterCRListItem margin="0"><a href="{() => false}">Blog</a></FooterCRListItem>
            <FooterCRListItem><a href="{() => false}">Facebook</a></FooterCRListItem>
            <FooterCRListItem><a href="{() => false}">Twitter</a></FooterCRListItem>
            <FooterCRListItem><a href="{() => false}">LinkedIn</a></FooterCRListItem>
            <FooterCRListItem><a href="{() => false}">Instagram</a></FooterCRListItem>
          </FooterCRList>
          <p> Site design / logo © 2022 Stack Exchange Inc; user contributions licensed under CC BY-SA. <span>rev 2022.12.19.43125</span></p>
        </FooterCopyright>
      </FooterContainer>
    </FooterEl>
  )
}

export default Footer;