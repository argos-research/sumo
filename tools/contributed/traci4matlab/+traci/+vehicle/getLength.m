function length = getLength(vehID)
%getLength Returns the length in m of the given vehicle.
%   length = getLength(VEHID) Returns the length in m of the given vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLength.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
length = traci.vehicle.getUniversal(constants.VAR_LENGTH, vehID);