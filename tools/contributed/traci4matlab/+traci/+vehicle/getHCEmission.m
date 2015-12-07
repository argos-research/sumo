function HCEmission = getHCEmission(vehID)
%getHCEmission Returns the HC emission of the vehicle.
%   HCEmission = getHCEmission(VEHID) Returns the HC emission in mg for
%   the last time step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
HCEmission = traci.vehicle.getUniversal(constants.VAR_HCEMISSION, vehID);