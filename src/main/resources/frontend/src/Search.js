import React, { Component } from "react";
import { Button, FormGroup, FormControl } from "react-bootstrap";
import Product from './Product.js';
import "./Search.css";

export default class Search extends Component {
    constructor(props) {
        super(props);

        this.state = {
            results: []
        };
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault();
    }

    render() {
        return (
            <div className="SearchContainer">
                <div className="Search">
                    <form onSubmit={this.handleSubmit}>
                        <FormGroup className="FormGroup" bsSize="large">
                            <FormControl
                                autoFocus
                                placeholder="Search.."
                                type="text"
                                value={this.state.search}
                                onChange={this.handleChange}
                            />
                        </FormGroup>
                        <Button
                            block
                            bsSize="large"
                            type="submit"
                        >
                            Hae
                        </Button>
                    </form>
                </div>
                <div className="ResultContainer">
                    { this.state.results.forEach(result => {
                        return (<Product name={result.name} auchtor={result.auchtor} isbn={result.isbn}  />)
                    })
                    }
                </div>
            </div>
        );
    }


}