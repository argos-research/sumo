function typeID = getTypeID(vehID)
%getTypeID Returns the id of the type of the named vehicle.
%   typeID = getTypeID(VEHID) Returns the id of the type of the named 
%   vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getTypeID.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
typeID = traci.vehicle.getUniversal(constants.VAR_TYPE, vehID);