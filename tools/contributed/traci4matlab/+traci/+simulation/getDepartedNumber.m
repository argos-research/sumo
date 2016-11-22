function departedNumber = getDepartedNumber()
%getDepartedNumber Get the number of departed vehicles.
%   departedNumber = getDepartedNumber() Returns the number of vehicles 
%   which departed (were inserted into the road network) in this time step. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getDepartedNumber.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
departedNumber = traci.simulation.getUniversal(constants.VAR_DEPARTED_VEHICLES_NUMBER);