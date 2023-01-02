import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Nav = styled.nav`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 4px;
    margin: 16px;
`;

const ButtonLink=styled(Link)`
    text-decoration: none;
`;

const Button = styled.button`
    display: grid;
    font-size: 18px;
    width: 30px;
    height: 27px;
    margin: 0 2px 0 2px;
    padding: 2px 0 0 0 ;
    text-align: center;
    border: 1px solid hsl(210,8%,85%);
    border-radius: 3px;
    cursor: pointer;

    :hover {
        background-color: hsl(210,8%,85%)
    }

    :active {
        color: white;
        background: #F48225;
        border: 1px solid #F48225;
    }

    &[aria-current] {
        color: #fff;
        background: #F48225;
        font-weight: bold;
    }
`;

const Pagenation = ({ total, limit, page, setPage}) => {
    const numPages = Math.ceil(total / limit);
    const reloadHelper = (i) => {
        setPage(i);
        localStorage.setItem("page", i);
        window.location.reload();
    }
    return (
        <>
            <Nav>
                <Button onClick={() => setPage(page - 1)} disabled={page === 1}>
                    &lt;
                </Button>
                {Array(numPages)
                    .fill()
                    .map((_, i) => (
                        <ButtonLink to={`/?page=${i+1}`} key={i + 1}>
                            <Button onClick={() => reloadHelper(i + 1)} aria-current={page === i + 1 || localStorage.getItem("page") == i + 1 ? "page" : null}>
                                {i + 1}
                            </Button>
                        </ButtonLink>
                    ))}
                <Button onClick={() => setPage(page + 1)} disabled={page === numPages}>
                    &gt;
                </Button>
            </Nav>
        </>
    );
}

export default Pagenation;