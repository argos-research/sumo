function loadedNumber = getLoadedNumber()
%getLoadedNumber Get the number loaded vehicles.
%   loadedNumber = getLoadedNumber() Returns the number of vehicles which 
%   were loaded in this time step. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLoadedNumber.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
loadedNumber = traci.simulation.getUniversal(constants.VAR_LOADED_VEHICLES_NUMBER);