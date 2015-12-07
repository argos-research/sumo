function departedNumber = getDepartedNumber()
%getDepartedNumber Get the number of departed vehicles.
%   departedNumber = getDepartedNumber() Returns the number of vehicles 
%   which departed (were inserted into the road network) in this time step. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
departedNumber = traci.simulation.getUniversal(constants.VAR_DEPARTED_VEHICLES_NUMBER);