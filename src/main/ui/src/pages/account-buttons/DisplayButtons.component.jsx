import React from "react";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";

function DisplayButtons() {

    const margin = {
        paddingTop: '80px',
    }

    return (
        <div align="center" verticalalign="center" style={margin}>
            <Button variant="contained" component={Link} to={'/transfer/create'} color="primary">
                Transfer Money
            </Button>
            <br/>
            <br/>
            <br/>
            <Button variant="contained" component={Link} to={'/payment/create'} color="primary">
                Register Payment
            </Button>
        </div>
    );
}

export default DisplayButtons;