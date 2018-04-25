import React, { Component } from "react";
import { Button, FormGroup, FormControl } from "react-bootstrap";
import Product from './Product.js';
import "./Search.css";

export default class Search extends Component {
    constructor() {
        super();
        this.state = {
            works: [],
        };
    }

    componentDidMount() {
        console.log("Hei");
        fetch('http://localhost/data/works', {mode: 'no-cors', headers: {
                'Access-Control-Allow-Origin': '*'
            }})
            .then(function(results) {
                return results.text().then(function(text) {
                    return text ? JSON.parse(text) : {}
                })
            }).then(function(data) {
            console.log("Mitä dataa tulee", data);
            //let works = data.json();
            //this.setState({works: works});
            //console.log("state", this.state.works);
        }
        )
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
                    { this.state.works.forEach(result => {
                        return (<Product id={result.id} auchtor={result.auchtor} name={result.name} isbn={result.isbn} published={result.published} genre={result.genre} type={result.type} weight={result.weight} products={result.products}/>)
                    })
                    }
                </div>
            </div>
        );
    }
}