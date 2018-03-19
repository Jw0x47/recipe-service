//
// Generic Ajax Resource Provider
//
import $ from 'jquery';
import {extend} from 'underscore';

class ResourceProvider {

  constructor(settings) {
    this.settings = settings;
    return this;
  }

  defaultSettings() {
    return {
      dataType: 'json',
      type: 'GET',
      contentType: 'application/json'
    };
  }

  send() {
    return $.ajax(extend(this.defaultSettings(), this.settings));
  }

}

export default ResourceProvider;