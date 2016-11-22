function position = getPosition(vehID)
%getPosition Returns the position of the named vehicle within the last step.
%   position = getPosition(VEHID) Returns the x,y position of the named 
%   vehicle within the last step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getPosition.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
position = traci.vehicle.getUniversal(constants.VAR_POSITION, vehID);